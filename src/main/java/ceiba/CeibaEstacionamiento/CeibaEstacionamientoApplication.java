package ceiba.CeibaEstacionamiento;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing
public class CeibaEstacionamientoApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(CeibaEstacionamientoApplication.class, args);
		
		//DateTime dt = new DateTime();
		//System.out.println("dia= " + dt.getMinuteOfDay());
		/*Date date = new Date(2018, 6, 27, 6, 00);
		System.out.println(date);
		Date date2 = new Date(2018, 6, 26, 9, 00);
		System.out.println(date2);
		
		DateTime dt1 = new DateTime(date);
		System.out.println(dt1);
		DateTime dt2 = new DateTime(date2);
		System.out.println(dt2);
		
		Duration duracionParqueo = new Duration(dt1, dt2);
		System.out.println(duracionParqueo.getStandardDays());*/
	}
}
