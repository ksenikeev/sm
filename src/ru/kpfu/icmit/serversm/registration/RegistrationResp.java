package ru.kpfu.icmit.serversm.registration;

/**
 * Класс для возврата методом, осуществляющим процедуру регистрации нового пользователя.
 * Возможны разные ситуации: регистрация прошла успешно (status=true, token=...),
 * данные от клиента испорчены (status=false, description="данные от клиента испорчены"),
 * пользователь с таким именем или логином уже существует (status=false, description="пользователь с таким именем или логином уже существует"),
 * ...
 */
public class RegistrationResp {
    public boolean status;
    public String description;
    public String token;
}
