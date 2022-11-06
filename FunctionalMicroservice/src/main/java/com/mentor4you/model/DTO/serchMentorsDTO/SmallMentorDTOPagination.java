package com.mentor4you.model.DTO.serchMentorsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class SmallMentorDTOPagination {
    private int size;
    private Page<SmallDataMentorDTO> listResult = new Page<SmallDataMentorDTO>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super SmallDataMentorDTO, ? extends U> function) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getNumberOfElements() {
            return 0;
        }

        @Override
        public List<SmallDataMentorDTO> getContent() {
            return null;
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return false;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator<SmallDataMentorDTO> iterator() {
            return null;
        }
    };

    public SmallMentorDTOPagination() {
    }

    public SmallMentorDTOPagination(int size, Page<SmallDataMentorDTO> listResult) {
        this.size = size;
        this.listResult = listResult;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Page<SmallDataMentorDTO> getListResult() {
        return listResult;
    }

    public void setListResult(Page<SmallDataMentorDTO> listResult) {
        this.listResult = listResult;
    }
}
