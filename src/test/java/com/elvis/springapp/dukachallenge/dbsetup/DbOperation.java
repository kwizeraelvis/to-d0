package com.elvis.springapp.dukachallenge.dbsetup;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

import java.util.Date;
import java.util.UUID;

public class DbOperation {

    public static Operation INSERT_USER = Operations
            .insertInto("task_owner")
            .columns("id","first_name","last_name","username","email","password")
            .values(UUID.fromString("4fbf908b-085f-41ca-bcf4-fa6303f3cfc9"),"Nishimwe","elysee","elysee","nishimwelys@gmail.com","$2a$10$1RiXcVJrEiinpXixe1GwQ.0yKCtisID.2y99PBG4O2oXtjYdpKyJi")
            .build();

    public static Operation INSERT_TODO = Operations
            .insertInto("task")
            .columns("id","title","description","priority","create_date","modified_date","owned_by_id")
            .values(UUID.fromString("4fbf908b-085f-41ca-bcf4-fa6303f3cfc9"),"Learning","To learn Math and Computer Science","LOW",new Date(),new Date(),UUID.fromString("4fbf908b-085f-41ca-bcf4-fa6303f3cfc9"))
            .build();

    public static Operation DELETE_TODO = Operations
            .deleteAllFrom("task");

    public static Operation DELETE_USER = Operations
            .deleteAllFrom("task_owner");
}
