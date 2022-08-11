package com.mediscreen.history;

import com.mediscreen.history.model.History;
import com.mediscreen.history.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HistoryApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(HistoryApplication.class);

    @Autowired
    private HistoryRepository historyRepository;

    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Start !");
        historyRepository.deleteAll();
        historyRepository.saveAll(
                List.of(
                        new History(
                                "6177a31824f1d205e0b0692d",
                                1L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-02-04T07:04:03.321Z"),
                                "Patient states that they are 'feeling terrific' Weight at or below recommended level"),
                        new History(
                                "6177a3648207e0587345067a",
                                2L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-10T07:04:03.321Z"),
                                "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late"),
                        new History(
                                "6177a39b665edf352cf74f90",
                                2L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-01T07:04:03.321Z"),
                                "Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic"),
                        new History(
                                "6178f9e3c2533871717abaf2",
                                3L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-27T07:04:03.321Z"),
                                "Patient states that they are short term Smoker"),
                        new History(
                                "6178fa80b2162e1fd560e33f",
                                3L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-27T07:04:03.321Z"),
                                "Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high"),
                        new History(
                                "618244a55ec6eb500c8b5be0",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication"),
                        new History(
                                "618273e087deb21060318688",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Patient states that they are experiencing back pain when seated for a long time"),
                        new History(
                                "618244a55eb6eb600c8b5be0",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level"),
                        new History(
                                "618273e087def21060318688",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction")
                )

        );
    }
}
