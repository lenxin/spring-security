package samples.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rob Winch
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
