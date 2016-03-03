import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class order_lines {
	int operation;
	int recordId;
	LinkedHashMap<String, Object> productLine;
	
	public LinkedHashMap<String, Object> getProductLine() {
		return productLine;
	}
	public void setProductLine(LinkedHashMap<String, Object> productLine) {
		this.productLine = productLine;
	}
	public order_lines(){
		operation = 0;
		recordId = 0;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
}
