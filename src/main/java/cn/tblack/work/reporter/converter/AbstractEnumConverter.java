package cn.tblack.work.reporter.converter;

import javax.persistence.AttributeConverter;

import cn.tblack.work.reporter.enums.PersistEnum2DB;

/**
 * @~*~用于枚举类型于数据库类型之间的转换
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
public class AbstractEnumConverter<ATTR extends Enum<ATTR> & PersistEnum2DB<DB>, DB>
		implements AttributeConverter<ATTR, DB> {

	private Class<ATTR> clazz;

	public AbstractEnumConverter(Class<ATTR> clazz) {
		this.clazz = clazz;
	}

	@Override
	public DB convertToDatabaseColumn(ATTR attribute) {
		return attribute == null ? null : attribute.getData();
	}

	@Override
	public ATTR convertToEntityAttribute(DB dbData) {

		if (dbData == null)
			return null;

		ATTR[] enums = clazz.getEnumConstants();
		for (ATTR e : enums) {
			if (e.getData().equals(dbData)) {
				return e;
			}
		}
		
		
		return null;
	}

}
