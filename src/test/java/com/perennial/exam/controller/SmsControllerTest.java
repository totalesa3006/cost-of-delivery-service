package com.perennial.exam.controller;


import com.perennial.exam.CostOfDeliveryApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { CostOfDeliveryApplication.class })
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DataJpaTest
@DirtiesContext
class SmsControllerTest {

//	@Autowired
//	private MockMvc mockMvc;
//
//	@Mock
//	private SmsServiceImpl smsServiceImplMock;
//	
//	@Mock
//	private UserDTO userDTO;
//
//	@Autowired
//	private JacksonTester<UserDTO> userDTOJson;
//
//	
//	@Test
//	public void testSaveSuccess() throws Exception {	
//	userDTO.setMobile("7743991153");
//	userDTO.setMessage("Your one time login password for Finaleap is This password will be valid for 10 minutes only");
//		
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/sms-service/sendOTP"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}	
}
