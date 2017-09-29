package com.hanbit.kakaotalk;

import java.util.ArrayList;

public class Service {
    public static interface IPredicate{
        public void excute();
    }
    public static interface IPost{
        public void excute(Object o);
    }
    public static interface IList{
        public ArrayList<?> excute(Object o);
    }
    public static interface IGet{
        public Object excute(Object o);
    }
    public static interface IPut{
        public void excute(Object o);
    }
    public static interface Delete{
        public void excute(Object o);
    }
}
