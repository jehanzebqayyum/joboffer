package joboffer.api.command;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import joboffer.command.CreateJob;
import joboffer.command.api.CommandController;
import joboffer.command.handler.CreateJobHandler;

@RunWith(SpringRunner.class)
@WebMvcTest(CommandController.class)
public class CommandControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private CreateJobHandler createJobHandler;

	@Test
	public void shouldHandleCreateJob() throws Exception {
		UUID id = UUID.randomUUID();
		CreateJob command = new CreateJob(id, "test", new Date());
		this.mockMvc
				.perform(put("/job").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf8")
						.content(objectMapper.writeValueAsString(command)))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().string(""));
	}

}
