package samples.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rob Winch
 */
@Repository
public interface SecurityMessageRepository extends MessageRepository {
	@Query("select m from Message m where m.to.id = ?#{ principal?.id }")
	List<Message> findAll();
}
