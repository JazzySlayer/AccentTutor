class BootStrap {
    def springSecurityService
    def init = { servletContext ->
        def adminRole = com.accenttutor.Role.findByAuthority('ROLE_ADMIN') ?: new com.accenttutor.Role(authority: 'ROLE_ADMIN').save(failOnError: true)
        def adminUser = com.accenttutor.User.findByUsername('admin') ?: new com.accenttutor.User( username: 'admin', password: 'admin', enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) { com.accenttutor.UserRole.create adminUser, adminRole }
        /*springSecurityService.encodePassword('admin')*/
    }
    def destroy = {
    }
}
