package cn.edu.uestc.cms.page.dialect;

import cn.edu.uestc.cms.page.Page;

public class MySqlDialect extends AbstractDialect {

	@Override
	public String getPageSql(String sql, Page<Object> page) {
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
		sqlBuilder.append(sql);
		if (page.getPageNo() == 0) {
			sqlBuilder.append(" LIMIT ");
			sqlBuilder.append(page.getPageSize());
		} else {
			sqlBuilder.append(" LIMIT ");
			sqlBuilder.append((page.getPageNo() -1 ) * page.getPageSize());
			sqlBuilder.append(",");
			sqlBuilder.append(page.getPageSize());
		}
		return sqlBuilder.toString();
	}

}
