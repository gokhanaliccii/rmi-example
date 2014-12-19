package enums;


public enum FilterTypes {

	Original(-1), BlurFilter(0), SharpenFilter(1), InvertFilter(2);

	int filterId;

	private FilterTypes(int id) {
		this.filterId = id;
	}

	public static FilterTypes get(int id) {
		
		switch (id) {
		case 0:		return BlurFilter;
		case 1:		return SharpenFilter;
		case 2:		return InvertFilter;
		
		default:  	return Original; 
		
		}
	}
	
	public int getFilterId() {

		return filterId;
	}
	
	public String getPath(){
		
		return name()+"_"+getFilterId();
	}
}
