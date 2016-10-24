package org.thiki.kanban.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 5/18/16.
 */
@Domain(order = DomainOrder.BOARD, name = "看板")
@RunWith(SpringJUnit4ClassRunner.class)
public class BoardAuthenticationTest extends TestBase {
    @Scenario("鉴权>当用户删除一个指定的board时,如果该用户并非board所属团队的成员,且board非个人所属,则不允许删除")
    @Test
    public void notAllowedIfCurrentHasNoAuthority() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_board (id,name,author,owner) VALUES ('fooId','board-name','someone','others')");
        given().header("userName", "someone")
                .when()
                .delete("/someone/boards/fooId")
                .then()
                .statusCode(401)
                .body("code", equalTo(BoardCodes.FORBID_CURRENT_IS_NOT_A_MEMBER_OF_THE_TEAM.code()))
                .body("message", equalTo(BoardCodes.FORBID_CURRENT_IS_NOT_A_MEMBER_OF_THE_TEAM.message()));
    }
}
