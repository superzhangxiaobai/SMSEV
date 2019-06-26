package com.xiaobai.smsev.sys.dao;

import com.xiaobai.smsev.sys.entity.MenuInfo;
import com.xiaobai.smsev.sys.entity.SysParam;
//import org.apache.ibatis.annotations.InsertProvider;
//import org.apache.ibatis.annotations.Options;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.SelectProvider;
//import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface MenuMapper {
//    @Select("select * from t_menu where roleid=#{roleid}")
    List<MenuInfo> getListByRoleid(Long roleid);
//    @Select(" select menu.* from t_user userinfo, t_user_role user_role, t_role role, t_role_menu role_menu, t_menu menu" +
//            " where userinfo.id=user_role.userid and user_role.roleid=role.id and role.id=role_menu.roleid and role_menu.menuid=menu.id" +
//            " and userid=#{userid}")
    List<MenuInfo> getListByUserid(Long userid);
//    @SelectProvider(type = DaoProvider.class, method = "find")
    //@Select(" select menu.* from t_menu menu where isEnable=#{isEnable,jdbcType=BIT} ")
    List<MenuInfo> getAll(SysParam param);

//    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
//    @InsertProvider(type=DaoProvider.class,method = "insert")
    int insert(MenuInfo menu);
//    @InsertProvider(type=DaoProvider.class,method = "update")
    int update(MenuInfo menu);


    class DaoProvider {
        private static final String tablename="t_menu";
        public String find(SysParam param) {
            String sql = "SELECT menu.* FROM t_menu menu where 1=1 ";
            if(param.getId()!=null){
                sql += " and id = #{id, jdbcType=VARCHAR}";
            }
            if(param.getPid()!=null){
                sql+=" and pid = #{pid, jdbcType=INT}";
            }
            sql+=" and isEnable = #{isEnable, jdbcType=BIT}";
            return sql;
        }
//        public String insert(MenuInfo param){
//            SQL sql = new SQL(); //SQL语句对象，所在包：org.apache.ibatis.jdbc.SQL
//            sql.INSERT_INTO(tablename);
//            if(param.getMenuname() != null){
//                sql.VALUES("menuname", "#{menuname}");
//            }
//            if(param.getUrl() != null){
//                sql.VALUES("url", "#{url}");
//            }
//            if(param.getPid() != null){
//                sql.VALUES("pid", "#{pid}");
//            }
//            if(param.getIcon() != null){
//                sql.VALUES("icon", "#{icon}");
//            }
//            sql.VALUES("isEnable", "#{isEnable,jdbcType=BIT}");
//            sql.VALUES("createtime", "#{createtime,jdbcType=DATE}");
//            sql.VALUES("creator", "#{createtime,jdbcType=VARCHAR}");
//            return sql.toString();
//        }
//        public String update(MenuInfo param){
//            return new SQL(){{
//                UPDATE(tablename);
//                if (!StringUtils.isEmpty(param.getMenuname())) {
//                    SET("menuname"," #{menuname,jdbcType=VARCHAR}");
//                }
//                if (!StringUtils.isEmpty(param.getUrl())) {
//                    SET("url"," #{url,jdbcType=VARCHAR}");
//                }
//                if (param.getPid()!=null) {
//                    SET("pid","#{pid,jdbcType=VARCHAR}");
//                }
//                SET("isEnable", "#{isEnable,jdbcType=BIT}");
//                SET("updatetime", "#{createtime,jdbcType=DATE}");
//                SET("updator", "#{createtime,jdbcType=VARCHAR}");
//                WHERE("id = #{id,jdbcType=INT}" );
//            }}.toString();
//        }
    }
}
