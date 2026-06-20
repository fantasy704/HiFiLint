package top.fantasy.hifilint.service;

interface IAdbUserService {
    String executeCommand(String command) = 1;
    void destroy() = 16777114;
}