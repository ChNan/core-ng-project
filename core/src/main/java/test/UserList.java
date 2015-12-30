package test;

import java.util.ArrayList;
import java.util.List;  
  
public class UserList {  
      
    private List<User> subList;  
    private List<User> allList;  
      
    public UserList(){  
        subList=new ArrayList<User>();  
        allList=new ArrayList<User>();  
          
        for(int i=0;i<3;i++){  
            User user=new User();  
            user.setAge(i);  
            user.setName("lyh"+i);  
            subList.add(user);  
        }  
          
        for(int i=0;i<10;i++){  
            User user=new User();  
            user.setAge(i);  
            user.setName("lyh"+i);  
            allList.add(user);  
        }  
    }  
      
    public static void main(String[] args){  
        UserList userList=new UserList();  
        userList.allList.removeAll(userList.subList);//调用removeAll方法  
          
        System.out.println(userList.allList.size());  
          
    }  
}  