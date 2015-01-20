package game;

public class GameData {
	
	
	/**
	 * 用来存储数据，
	 * @param type 
	 * 为int型，指明要保存的数据类别。data为String型，为要存储的数据。
	 */
	public void storeData(int type,String data){
		
	}
	
	/**
	 * @param type 
	 * 为int类型，需要的数据。
	 * @return 为String类型
	 */
	public String loadData(int type){
		switch (type) {
		case 1:
			
			break;

		default:
			break;
		}
		return null;
	}
}
