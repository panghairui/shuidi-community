package com.shuidi.community.dao;

import com.shuidi.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @Description: 登录凭证数据访问层
 * @Author: panghairui
 * @Date: 2022/3/29 4:50 下午
 */
@Mapper
public interface LoginTicketDao {

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

}
