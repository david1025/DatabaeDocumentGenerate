package tj.david.entity;

public class TableInfo {

	private String name;
	private String comment;
	private String mk;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMk() {
		
		if("PRI".equals(mk)){
			return "主键";
		} else if("类型".equals(mk)) {
			return mk;
		} else {
			return "";
		}
	}

	public void setMk(String mk) {
		this.mk = mk;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
