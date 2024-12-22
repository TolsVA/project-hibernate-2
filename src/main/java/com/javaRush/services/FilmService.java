package com.javaRush.services;

import com.javaRush.dao.DAOFactory;
import com.javaRush.dao.*;
import com.javaRush.entity.*;
import com.javaRush.util.SessionUtil;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmService {

    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final ActorDAO actorDAO;
    private final CategoryDAO categoryDAO;
    private final LanguageDAO languageDAO;
    private final SessionUtil sessionUtil;

    public FilmService(SessionUtil sessionUtil) {
        this.filmDAO = DAOFactory.getFilmDAO();
        this.filmTextDAO = DAOFactory.getFilmTextDAO();
        this.actorDAO = DAOFactory.getActorDAO();
        this.categoryDAO = DAOFactory.getCategoryDAO();
        this.languageDAO = DAOFactory.getLanguageDAO();
        this.sessionUtil = sessionUtil;
    }

    public void addNewFilm() {
        sessionUtil.executeInTransaction(_ -> {
            languageDAO.getItems(0, 20)
                    .stream()
                    .unordered()
                    .findAny()
                    .ifPresentOrElse(
                            language -> {
                                List<Actor> actors = actorDAO.getItems(0, 20);
                                List<Category> categories = categoryDAO.getItems(0, 5);

                                Film film = new Film();
                                film.setActors(new HashSet<>(actors));
                                film.setLanguage(language);
                                film.setCategories(new HashSet<>(categories));
                                film.setTitle("the best fantastic");
                                film.setReleaseYear(Year.now());
                                film.setRentalDuration((byte) 45);
                                film.setRentalRate(BigDecimal.ZERO);
                                film.setRating(Rating.NC17);
                                film.setReplacementCost(BigDecimal.TEN);
                                film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
                                film.setLength((short) 123);
                                film.setDescription("new fantastic film");
                                film.setOriginalLanguage(language);
                                filmDAO.save(film);

                                FilmText filmText = new FilmText();
                                filmText.setFilm(film);
                                filmText.setId(film.getId());
                                filmText.setDescription(film.getDescription());
                                filmText.setTitle(film.getTitle());
                                filmTextDAO.save(filmText);
                            },
                            () -> {
                                // Обработка случая, если language не найден
                                throw new RuntimeException("Language not found");
                            }
                    );
            return null;
        });
    }
}