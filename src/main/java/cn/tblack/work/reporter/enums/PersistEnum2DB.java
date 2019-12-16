package cn.tblack.work.reporter.enums;

import java.io.Serializable;

public interface PersistEnum2DB<DB> extends Serializable{
	
	DB getData();
}
