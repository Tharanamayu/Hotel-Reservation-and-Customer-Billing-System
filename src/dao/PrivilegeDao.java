/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Module;
import entity.Privilege;
import entity.Role;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tharana
 */
public class PrivilegeDao {
    
    public static ObservableList<Privilege> getAll() {     
        
        return CommonDao.select("Privilege.findAll");
        
    }
    
    public static ObservableList<Privilege> getAllPrivilege() {     
        
        return CommonDao.selectAllPrivilege("Privilege.findAll");
        
    }

    public static void add(Privilege privilege) {
        CommonDao.insert(privilege);
    }
    public static void update(Privilege privilege) {
        
        CommonDao.update(privilege);
    }
    public static void delete(Privilege privilege) {
        
        CommonDao.delete(privilege);
    }

    public static Privilege getById(Integer id) {
         HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Privilege> list = CommonDao.selectAllPrivilege("Privilege.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    public static Privilege getByIdPrivilege(Integer id) {
         HashMap hmap = new HashMap();
        hmap.put("id", id);

        ObservableList<Privilege> list = CommonDao.selectAllPrivilege("Privilege.findById", hmap);
        
        if (list == null) {
            return null;
        } else {
            return list.get(0);
        }
    }

//    public static ObservableList<Privilege> getAllByRole(Role role) {
//        HashMap hmap = new HashMap();
//        hmap.put("role", role);
//
//        return CommonDao.select("Privilege.findAllByRole", hmap);
//    }
    public static ObservableList<Privilege> getAllByRolePrivilege(Role role) {
        HashMap hmap = new HashMap();
        hmap.put("role", role);

        return CommonDao.selectAllByRolePrivilege("Privilege.findAllByRole", hmap);
    }

//    public static ObservableList<Privilege> getAllByModule(Module module) {
//        HashMap hmap = new HashMap();
//        hmap.put("module", module);
//
//        return CommonDao.select("Privilege.findAllByModule", hmap);
//    }
    public static ObservableList<Privilege> getAllByModulePrivilege(Module module) {
        HashMap hmap = new HashMap();
        hmap.put("module", module);

        return CommonDao.selectAllByModulePrivilege("Privilege.findAllByModule", hmap);
    }

//    public static ObservableList<Privilege> getAllByRoleModule(Role role, Module module) {
//       HashMap hmap = new HashMap();
//       hmap.put("role", role);
//       hmap.put("module", module);
//
//       return CommonDao.select("Privilege.findAllByRoleModule", hmap);
//       
//    }
    public static ObservableList<Privilege> getAllByRoleModulePrivilege(Role role, Module module) {
       HashMap hmap = new HashMap();
       hmap.put("role", role);
       hmap.put("module", module);

       return CommonDao.selectAllByRoleModulePrivilege("Privilege.findAllByRoleModule", hmap);
       
    }
}
