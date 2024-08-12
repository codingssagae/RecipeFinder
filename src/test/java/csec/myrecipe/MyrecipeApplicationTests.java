package csec.myrecipe;

import csec.myrecipe.domain.Member;
import csec.myrecipe.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyrecipeApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void addMember(){
		Member member = new Member();
		member.setUsername("testuser");
		memberRepository.save(member);
	}

}
