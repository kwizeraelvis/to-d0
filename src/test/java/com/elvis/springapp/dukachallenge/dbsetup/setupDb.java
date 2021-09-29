package com.elvis.springapp.dukachallenge.dbsetup;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

public class setupDb {
    public void execute(Operation operation){
        DbSetup db = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/duka","postgres","Elysee@123"),operation);
        db.launch();
    }
}
