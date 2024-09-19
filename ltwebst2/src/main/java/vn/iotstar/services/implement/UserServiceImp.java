package vn.iotstar.services.implement;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.implement.UserDaoImp;
import vn.iotstar.models.UserModel;
import vn.iotstar.services.IUserService;

public class UserServiceImp implements IUserService {

	IUserDao userDao = new UserDaoImp(); //Lay toan bo ham trong tang DAO cua user
	
	@Override
	public UserModel login(String username, String password) {
		// TODO Auto-generated method stub
		UserModel user = this.findByUserName(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public UserModel findByUserName(String username) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(username);
	}
	
	

}
