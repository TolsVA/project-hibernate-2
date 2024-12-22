package com.javaRush.dao;

import com.javaRush.config.HibernateUtil;
import org.hibernate.SessionFactory;

public class DAOFactory {

    private static volatile ActorDAO actorDAO;
    private static volatile AddressDAO addressDAO;
    private static volatile CategoryDAO categoryDAO;
    private static volatile CityDAO cityDAO;
    private static volatile CountryDAO countryDAO;
    private static volatile CustomerDAO customerDAO;
    private static volatile FilmDAO filmDAO;
    private static volatile FilmTextDAO filmTextDAO;
    private static volatile InventoryDAO inventoryDAO;
    private static volatile LanguageDAO languageDAO;
    private static volatile PaymentDAO paymentDAO;
    private static volatile RentalDAO rentalDAO;
    private static volatile StaffDAO staffDAO;
    private static volatile StoreDAO storeDAO;

    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private static <T> T getDAO(Class<T> daoClass) {
        String daoFieldName = getDaoFieldName(daoClass);

        try {
            // Получаем соответствующее поле класса DAOFactory
            java.lang.reflect.Field field = DAOFactory.class.getDeclaredField(daoFieldName);

            // Сохраняем текущую доступность поля
            boolean wasAccessible = field.isAccessible();

            // Разрешаем доступ к приватному полю
            field.setAccessible(true);

            try {
                // Проверка, если DAO ещё не создан
                T dao = (T) field.get(null);
                if (dao == null) {
                    synchronized (DAOFactory.class) {
                        dao = (T) field.get(null);
                        if (dao == null) {
                            // Если DAO ещё не создано, создаём его с помощью конструктора
                            dao = daoClass.getConstructor(SessionFactory.class).newInstance(sessionFactory);
                            field.set(null, dao);  // Устанавливаем созданное значение в поле
                        }
                    }
                }

                return dao;

            } finally {
                // Восстанавливаем исходное состояние доступности поля
                field.setAccessible(wasAccessible);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error creating DAO instance for " + daoClass.getSimpleName(), e);
        }
    }

    private static String getDaoFieldName(Class<?> daoClass) {
        // Получаем имя класса DAO и преобразуем его в имя поля (в нижнем регистре)
        String daoName = daoClass.getSimpleName();
        return daoName.substring(0, 1).toLowerCase() + daoName.substring(1);
    }


    public static ActorDAO getActorDAO() {
        return actorDAO != null ? actorDAO : getDAO(ActorDAO.class);
    }

    public static AddressDAO getAddressDAO() {
        return addressDAO != null ? addressDAO : getDAO(AddressDAO.class);
    }

    public static CategoryDAO getCategoryDAO() {
        return categoryDAO != null ? categoryDAO : getDAO(CategoryDAO.class);
    }

    public static CityDAO getCityDAO() {
        return cityDAO != null ? cityDAO : getDAO(CityDAO.class);
    }

    public static CountryDAO getCountryDAO() {
        return countryDAO != null ? countryDAO : getDAO(CountryDAO.class);
    }

    public static CustomerDAO getCustomerDAO() {
        return customerDAO != null ? customerDAO : getDAO(CustomerDAO.class);
    }

    public static FilmDAO getFilmDAO() {
        return filmDAO != null ? filmDAO : getDAO(FilmDAO.class);
    }

    public static FilmTextDAO getFilmTextDAO() {
        return filmTextDAO != null ? filmTextDAO : getDAO(FilmTextDAO.class);
    }

    public static InventoryDAO getInventoryDAO() {
        return inventoryDAO != null ? inventoryDAO : getDAO(InventoryDAO.class);
    }

    public static LanguageDAO getLanguageDAO() {
        return languageDAO != null ? languageDAO : getDAO(LanguageDAO.class);
    }

    public static PaymentDAO getPaymentDAO() {
        return paymentDAO != null ? paymentDAO : getDAO(PaymentDAO.class);
    }

    public static RentalDAO getRentalDAO() {
        return rentalDAO != null ? rentalDAO : getDAO(RentalDAO.class);
    }

    public static StaffDAO getStaffDAO() {
        return staffDAO != null ? staffDAO : getDAO(StaffDAO.class);
    }

    public static StoreDAO getStoreDAO() {
        return storeDAO != null ? storeDAO : getDAO(StoreDAO.class);
    }
}