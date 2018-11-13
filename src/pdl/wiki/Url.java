package pdl.wiki;

public class Url
{
    private String link;
    private boolean valid;
    private int tableCount;
    
    public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public int getTableCount() {
		return tableCount;
	}
	public void setTableCount(int tableCount) {
		this.tableCount = tableCount;
	}
}
