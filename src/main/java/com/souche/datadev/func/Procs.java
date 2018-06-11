package com.souche.datadev.func;

import com.souche.datadev.func.Proc;
import com.souche.datadev.func.Proc1;

/**
 * Created by chauncy on 2018/6/11.
 */
public class Procs {

    public static void invoke(Proc proc) {
        if (proc != null)
            proc.run();
    }

    public static <T1> void invoke(Proc1<T1> proc, T1 t1) {
        if (proc != null)
            proc.run(t1);
    }







}
