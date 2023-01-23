package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate; // 주입받을 수 있는 것은 아니다

    // @Autowired 생성자가 한개만 있으면 @Autowired를 생략할 수 있다.
    public JdbcTemplateMemberRepository(DataSource dataSource) { // datasource를 주입받는다.
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) throws SQLException {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // insert문을 만들어줌
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        // member : 테이블명, id : 넣고자하는 키

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new // executeAndReturnKey를 통해 member를 받고 setId로 값을 넣어준다
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny(); // Optional로 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny(); // Optional로 반환
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    /*
        RowMapper란?
        -> 데이터베이스의 반환 결과인 ResultSet을 객체로 변환해주는 클래스
     */

    /*
    private RowMapper<Member> memberRowMapper(){
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                // ResultSet 값을 Member 객체에 저장
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));

                // member 반환
                return member;
            };
        };
    }
    */

    // 람다식으로 변경 가능
    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            // ResultSet 값을 Member 객체에 저장
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            // member 반환
            return member;
        };
    }
}
