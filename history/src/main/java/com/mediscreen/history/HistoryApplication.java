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
                                "Le patient déclare qu'il « se sent bien » Poids égal ou inférieur au niveau recommandé"),
                        new History(
                                "6177a3648207e0587345067a",
                                2L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-10T07:04:03.321Z"),
                                "Le patient déclare qu'il ressent beaucoup de stress au travail Le patient se plaint également que son audition semble anormale ces derniers temps"),
                        new History(
                                "6177a39b665edf352cf74f90",
                                2L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-01T07:04:03.321Z"),
                                "Le patient déclare avoir eu une réaction aux médicaments au cours des 3 derniers mois Le patient se plaint également avoir des vertige depuis 1 semaine"),
                        new History(
                                "6178f9e3c2533871717abaf2",
                                3L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-27T07:04:03.321Z"),
                                "Le patient déclare qu'il est un fumeur à court terme"),
                        new History(
                                "6178fa80b2162e1fd560e33f",
                                3L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-10-27T07:04:03.321Z"),
                                "Le patient déclare avoir arrêté de fumer au cours de l'année dernière Le patient se plaint également d'épisodes respiratoires anormaux Rapports de laboratoire Cholestérol LDL élevé"),
                        new History(
                                "618244a55ec6eb500c8b5be0",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Le patient déclare qu'il est devenu difficile de monter les escaliers Le patient se plaint également d'être essoufflé Les résultats de laboratoire indiquent que les anticorps sont élevés Réaction aux médicaments"),
                        new History(
                                "618273e087deb21060318688",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Le patient taille normal déclare qu'il souffre de maux de dos anormal lorsqu'il est assis pendant une longue période hémoglobine A1C"),
                        new History(
                                "618244a55eb6eb600c8b5be0",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Le patient déclare qu'il est un fumeur à court terme Hémoglobine A1C au-dessus du niveau recommandé"),
                        new History(
                                "618273e087def21060318688",
                                4L,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-03T08:13:25.518Z"),
                                "Le patient déclare que la taille corporelle, le poids corporel, le cholestérol, les étourdissements et la réaction")
                )

        );

        historyRepository.findAllByPatientId(4).forEach((history) -> {
            logger.info("{}", history);
        });
    }
}
