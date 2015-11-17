package de.bxservice.bxpos.logic.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Every new role supported by the app must be registered here
 * Created by Diego Ruiz on 17/11/15.
 */
public class PosRoles {

    public static final String WAITER_ROLE     = "waiter_role";
    public static final String SUPERVISOR_ROLE = "supervisor_role";

    private HashMap<String, String> roles1 = new HashMap<String, String>();

    public static ArrayList<String> getRoles(){
        ArrayList<String> roles = new ArrayList<String>();

        roles.add(WAITER_ROLE);
        roles.add(SUPERVISOR_ROLE);

        return roles;
    }

}