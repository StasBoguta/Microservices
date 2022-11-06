package com.mentor4you.service;

import com.mentor4you.exception.MenteeNotFoundException;
import com.mentor4you.model.ContactsToAccounts;
import com.mentor4you.model.DTO.MenteeResponseDTO;
import com.mentor4you.model.TypeContacts;
import com.mentor4you.repository.AccountRepository;
import com.mentor4you.repository.ContactsToAccountsRepository;
import com.mentor4you.repository.TypeContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ContactsToAccountsService {
    @Autowired
    ContactsToAccountsRepository contactsToAccountsRepository;
    TypeContactsRepository typeContactsRepository;
    AccountRepository accountRepository;
    TypeContactsService typeContactsService;

    public ContactsToAccountsService(ContactsToAccountsRepository contactsToAccountsRepository,
                                     TypeContactsRepository typeContactsRepository,
                                     AccountRepository accountRepository,
                                     TypeContactsService typeContactsService) {
        this.contactsToAccountsRepository = contactsToAccountsRepository;
        this.typeContactsRepository = typeContactsRepository;
        this.accountRepository = accountRepository;
        this.typeContactsService = typeContactsService;
    }

    //Create new row in ContactsToAccount table
    public String createNewContactData(int accountId, String typeCont, String contDate) {
        ContactsToAccounts contactsToAccounts = new ContactsToAccounts();
        contactsToAccounts.setAccounts(accountRepository.getById(accountId));
        TypeContacts typeContacts = typeContactsRepository.findByName(typeCont);
        if (typeContacts != null) {
            contactsToAccounts.setTypeContacts(typeContacts);
            contactsToAccounts.setContactData(contDate);
            contactsToAccountsRepository.save(contactsToAccounts);
            return "New raw was created";
        } else {
            return typeCont + "not found";
        }
    }


    public void changeContactsDataUser(MenteeResponseDTO request, int id){
    /**
     * Данные с веба могут быть:
     * такие как в базе
     * измененные
     * более полные (новая соц сеть)
     * другие
     * <p>
     * Если с фронта пришло что-то типа "LinkedIn":"" значит
     * нужно удалить эту запись из базы если она была но пока записываем так как есть
     * <p>
     * Если с фронта пришло что-то типа "LinkedIn":"" и в базе нет
     * этого typeContact у юзера значит не добавляем
     */
    //создадим мапу для удобства поиска
    Map<String, ContactsToAccounts> socialMapBD = new HashMap<>();

    //находим все записи в табличке ContactsToAccount для одного юзера по id
    List<ContactsToAccounts> listContToAcc = contactsToAccountsRepository.findAllByAccounts(id);

    //Пришло с веба. Map<TypeCont, ContData>
    Map<String, String> socialMapWeb = request.getSocialMap();

    //если в базе записи с контактными данными есть то перегоняем в мапу
                if(listContToAcc.size()>0)

    {
        //заполняем мапу
        for (ContactsToAccounts tp : listContToAcc) {
            socialMapBD.put(tp.getTypeContacts().getName(), tp);
        }
        //теперь есть две мапы, одна с сервера по Id юзера, вторая с веба
        //нужно их сравнить и применить изменения
        //идем по мапе с веба(она новая) и применяем изменения
        Iterator<Map.Entry<String, String>> iter = socialMapWeb.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            /**
             * если значение пустое например "LinkedIn":"" удаляем пару из карты
             * но, может быть так что пользователь удалил специально свою ссылку на ресурс
             * и нужно удалить из базы
             */

            if ("".equalsIgnoreCase(entry.getValue())) {
                //если записи с таким TypeContact у юзера были, удаляем их из базы
                if (socialMapBD.containsKey(entry.getKey())) {
                    int conToAccId = (socialMapBD.get(entry.getKey())).getId();
                    contactsToAccountsRepository.deleteRowById(conToAccId);
                    iter.remove();
                } else {
                    iter.remove();
                }
            }
            // данные не пустые например "LinkedIn":"hppts://blabla"
            else {
                //проверяем была ли у юзера такая соцСеть
                if (socialMapBD.containsKey(entry.getKey())) {
                    String contactDataFromDB = (socialMapBD.get(entry.getKey()).getContactData());
                    String contactDataFromWEB = entry.getValue();

                    //Может быть так что данные пришли без изменений
                    //данные одинаковые ничего не делаем
                    if (!contactDataFromWEB.equals(contactDataFromDB)) {
                        //если тип "Соц сеть" одинаковый, но данные разные нужно обновить, а не добавлять
                        ContactsToAccounts contactsToUpdate = socialMapBD.get(entry.getKey());
                        contactsToUpdate.setContactData(contactDataFromWEB);
                        contactsToAccountsRepository.save(contactsToUpdate);
                    }
                }
                // если пришли сюда значит юзер добавляет новую социальную сеть
                // новая для себя или для всего сайта
                // мы не верим юзеру и новую соцсеть не добавляем
                else if (typeContactsService.isTypeContactsExist(entry.getKey())) {
                    createNewContactData(id, entry.getKey(), entry.getValue());
                } else {
                    //TODO может возникнуть ситуация если юзер хочет сохранить ссылку на соцсеть которой нет
                    throw new MenteeNotFoundException("Mentee wonts to add a network, witch is not in Mentor4you = " + entry.getKey());
                }
            }
        }
    }
    // юзер пустой значит все записи с веба добавляем в базу
    // кроме пустых "LinkedIn":""
        else

    {
        for (Map.Entry<String, String> entry : socialMapWeb.entrySet()) {
            //TODO может возникнуть ситуация если юзер хочет сохранить ссылку на соцсеть которой нет
            //если пустые   "LinkedIn":""
            if (entry.getValue().equals("")) {
                //do nothing
            } else {
                if (typeContactsService.isTypeContactsExist(entry.getKey())) {
                    createNewContactData(id, entry.getKey(), entry.getValue());
                } else {
                    throw new MenteeNotFoundException("Mentee wonts to add a network, witch is not in Mentor4you = " + entry.getKey());
                }
            }
        }
    }
}

}
