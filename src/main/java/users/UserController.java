package users;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping(value="/api/v1")
public class UserController {

  //  private final AtomicLong counter = new AtomicLong();
//    private final long new_id= counter.incrementAndGet();
	private String ig_gen;
	private long id_no=12344;
	private long lib_card_id=34566;
	private long web_login_id=45677;
	private long bank_login_id=5001233;
   // private final List li= new ArrayList();
    Map<String, UserInfo> userData = new HashMap<String, UserInfo>();
Map<String, List<Map<String,CardInfo>>> cardData = new HashMap<String, List<Map<String,CardInfo>>>();
Map<String, List<Map<String,LoginInfo>>> loginData = new HashMap<String, List<Map<String,LoginInfo>>>();

Map<String, List<Map<String,BankInfo>>> bankData = new HashMap<String, List<Map<String,BankInfo>>>();


/*--------------------Add new User--------------------------*/

    @RequestMapping(value="/users", method=RequestMethod.POST)
@ResponseBody
   public UserInfo update(@Valid @RequestBody UserInfo userinfo){


id_no= id_no+1;
String user_id= "u"+Long.toString(id_no);	
Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
String created_at= sdf.format(date);
String updated_at= sdf.format(date);

UserInfo new_user=new UserInfo(user_id, userinfo.getEmail(), userinfo.getPassword(), created_at, updated_at);
    	userData.put(user_id,new_user);

	return new_user;
}

/*------------------Get one user----------------------------*/
    @RequestMapping(value="/users/{user_id}", 			method=RequestMethod.GET)
   public UserInfo view(@PathVariable("user_id") String user_id){
	System.out.println(user_id);
	return userData.get(user_id);
	
	

/*Iterator it = li.listIterator();

while (it.hasNext()) {
		System.out.println(it.next());
	}
*/
/*	while (it.hasNext()) {
		UserInfo temp= (UserInfo)it.next();
		if(temp.getUser_id().equals(user_id)){
			System.out.println("match found");
			curr_user=new UserInfo(
				temp.getUser_id(), 									temp.getEmail(),
				temp.getPassword(),
				temp.getCreated_at(),
				temp.getUpdated_at());

		 	break;   	
		}
		System.out.println(it.next());
	}
*/
		/*	System.out.println("no match found");

	return curr_user;*/
}
/*--------------------Update User-----------------------------*/
    @RequestMapping(value="/users/{user_id}", 			method=RequestMethod.PUT)
@ResponseBody
public UserInfo edit(@Valid @RequestBody UserInfo userinfo,@PathVariable("user_id") String user_id){
	System.out.println(user_id);
	UserInfo up_user= userData.get(user_id);
	up_user.setEmail(userinfo.getEmail());
	up_user.setPassword(userinfo.getPassword());
	Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
	String updated_at= sdf.format(date);
	up_user.setUpdated_at(updated_at);
	userData.put(user_id, up_user);
	return userData.get(user_id);
	


}


/*----------------------------End of User Module-------*/


/*---------------------Add a Library Card---------------------*/



@RequestMapping(value="/users/{user_id}/idcards", method=RequestMethod.POST)
@ResponseBody   
public CardInfo addCard(@Valid @RequestBody CardInfo cardinfo, @PathVariable("user_id") String user_id){


lib_card_id= lib_card_id+1;
String card_id= "c"+Long.toString(lib_card_id);	
/*Calendar date = Calendar.getInstance();
Date date = new Date();
date.add(Calendar.YEAR,1);
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
String expiration_date=sdf.format(date);

*/
CardInfo new_card=new CardInfo(card_id, cardinfo.getCard_no(), cardinfo.getCard_name(), cardinfo.getExpiration_date());

int flag=0;
Iterator it = cardData.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        if(pairs.getKey().equals(user_id)){
		List<Map<String, CardInfo>> temp_li=(List<Map<String,CardInfo>>)cardData.get(user_id);
		Map<String, CardInfo> map_new_card= new HashMap<String, CardInfo>();
		map_new_card.put(card_id, new_card);
		temp_li.add(map_new_card);
		flag=1;
		break;
	}
        
    }

if(flag==0){
	List<Map<String,CardInfo>> li= new ArrayList<Map<String,CardInfo>>();
	Map<String, CardInfo> map_new_card= new HashMap<String, CardInfo>();
	map_new_card.put(card_id, new_card);
	li.add(map_new_card);
	cardData.put(user_id,li);
}
	return new_card;
}


/*------------------List all Cards--------------------*/



@RequestMapping(value="/users/{user_id}/idcards", method=RequestMethod.GET)
   public List<Map<String,CardInfo>> viewAllCards(@PathVariable("user_id") String user_id){

	System.out.println(user_id);
	return cardData.get(user_id);
	
	
}
/*------------------Delete a card-------------------*/
@RequestMapping(value="/users/{user_id}/idcards/{card_id}", method=RequestMethod.DELETE)
@ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteCard(@PathVariable("user_id") String user_id, @PathVariable("card_id") String card_id){

//	System.out.println(user_id);
	int flag=0;
Iterator it = cardData.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        if(pairs.getKey().equals(user_id)){
		List<Map<String,CardInfo>> temp_li=(List<Map<String,CardInfo>>)cardData.get(user_id);
		
		Iterator it_list = temp_li.listIterator();

		while (it_list.hasNext()) {
			if(((Map<String,CardInfo>)it_list.next()).containsKey(card_id)){
				it_list.remove();
			}		
		}
		break;
	}
        
    }
	
}

/*----------------------------End of Library Module-------*/


/*----------------Add login data------------*/



@RequestMapping(value="/users/{user_id}/weblogins", method=RequestMethod.POST)
@ResponseBody 
  public LoginInfo addLogin(@Valid @RequestBody LoginInfo logininfo, @PathVariable("user_id") String user_id){


web_login_id= web_login_id+1;
String login_id= "l"+Long.toString(web_login_id);	
/*Calendar date = Calendar.getInstance();
Date date = new Date();
date.add(Calendar.YEAR,1);
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
String expiration_date=sdf.format(date);

*/
LoginInfo new_login=new LoginInfo(login_id, logininfo.getUrl(), logininfo.getLogin(), logininfo.getPassword());

int flag=0;
Iterator it = loginData.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        if(pairs.getKey().equals(user_id)){
		List<Map<String, LoginInfo>> temp_li=(List<Map<String,LoginInfo>>)loginData.get(user_id);
		Map<String, LoginInfo> map_new_login= new HashMap<String, LoginInfo>();
		map_new_login.put(login_id, new_login);
		temp_li.add(map_new_login);
		flag=1;
		break;
	}
        
    }

if(flag==0){
	List<Map<String,LoginInfo>> li= new ArrayList<Map<String,LoginInfo>>();
	Map<String, LoginInfo> map_new_login= new HashMap<String, LoginInfo>();
	map_new_login.put(login_id, new_login);
	li.add(map_new_login);
	loginData.put(user_id,li);
}
	return new_login;
}


/*------------------List all Logins--------------------*/



@RequestMapping(value="/users/{user_id}/weblogins", method=RequestMethod.GET)
   public List<Map<String,LoginInfo>> viewAllLogins(@PathVariable("user_id") String user_id){

	System.out.println(user_id);
	return loginData.get(user_id);
	
	
}
/*------------------Delete a web login-------------------*/
@RequestMapping(value="/users/{user_id}/weblogins/{login_id}", method=RequestMethod.DELETE)
@ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteLogin(@PathVariable("user_id") String user_id, @PathVariable("login_id") String login_id){

//	System.out.println(user_id);
	int flag=0;
Iterator it = loginData.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        if(pairs.getKey().equals(user_id)){
		List<Map<String,LoginInfo>> temp_li=(List<Map<String,LoginInfo>>)loginData.get(user_id);
		
		Iterator it_list = temp_li.listIterator();

		while (it_list.hasNext()) {
			if(((Map<String,LoginInfo>)it_list.next()).containsKey(login_id)){
				it_list.remove();
			}		
		}
		break;
	}
        
    }
	

}

/*----------------------------End of Web Module-------*/
/*----------------Add Bank data------------*/



@RequestMapping(value="/users/{user_id}/bankaccounts", method=RequestMethod.POST)
@ResponseBody
   public BankInfo addbank(@Valid @RequestBody BankInfo bankinfo, @PathVariable("user_id") String user_id){


bank_login_id= bank_login_id+1;
String ba_id= "b"+Long.toString(bank_login_id);	
/*Calendar date = Calendar.getInstance();
Date date = new Date();
date.add(Calendar.YEAR,1);
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
String expiration_date=sdf.format(date);

*/
BankInfo new_bank=new BankInfo(ba_id, bankinfo.getAccount_name(), bankinfo.getRouting_number(), bankinfo.getAccount_number());

int flag=0;
Iterator it = bankData.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        if(pairs.getKey().equals(user_id)){
		List<Map<String, BankInfo>> temp_li=(List<Map<String,BankInfo>>)bankData.get(user_id);
		Map<String, BankInfo> map_new_bank= new HashMap<String, BankInfo>();
		map_new_bank.put(ba_id, new_bank);
		temp_li.add(map_new_bank);
		flag=1;
		break;
	}
        
    }

if(flag==0){
	List<Map<String,BankInfo>> li= new ArrayList<Map<String,BankInfo>>();
	Map<String, BankInfo> map_new_bank= new HashMap<String, BankInfo>();
	map_new_bank.put(ba_id, new_bank);
	li.add(map_new_bank);
	bankData.put(user_id,li);
}
	return new_bank;
}


/*------------------List all Banks--------------------*/



@RequestMapping(value="/users/{user_id}/bankaccounts", method=RequestMethod.GET)
   public List<Map<String,BankInfo>> viewAllBanks(@PathVariable("user_id") String user_id){

	System.out.println(user_id);
	return bankData.get(user_id);
	
	
}
/*------------------Delete a bank login-------------------*/
@RequestMapping(value="/users/{user_id}/bankaccounts/{ba_id}", method=RequestMethod.DELETE)
@ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteBank(@PathVariable("user_id") String user_id, @PathVariable("ba_id") String ba_id){

//	System.out.println(user_id);
	int flag=0;
Iterator it = bankData.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        if(pairs.getKey().equals(user_id)){
		List<Map<String,BankInfo>> temp_li=(List<Map<String,BankInfo>>)bankData.get(user_id);
		
		Iterator it_list = temp_li.listIterator();

		while (it_list.hasNext()) {
			if(((Map<String,BankInfo>)it_list.next()).containsKey(ba_id)){
				it_list.remove();
			}		
		}
		break;
	}
        
    }
	
}
/*----------------------------End of Bank Module-------*/




}

