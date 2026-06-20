package top.fantasy.hifilint.service

class IAdbUserServiceImpl : IAdbUserService.Stub() {
    override fun executeCommand(command: String?): String {
        return try {
            val process = Runtime.getRuntime().exec(arrayListOf("sh", "-c", command).toTypedArray())
            process.inputStream.bufferedReader().readText()
        }catch (e: Exception){
            "EXCEPTION: ${e.message}"
        }
    }

    override fun destroy() {
        System.exit(0)
    }
}