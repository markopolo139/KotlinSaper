package pl.ms.saper.app.data.entites

import javax.persistence.*

@Entity
@Table(name = "App_Users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    var userId: Int?,
    var username: String?,
    var userPassword: String?,
    var email: String?,
    var passwordToken: String? = null
) {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "User_Roles",
        joinColumns = [ JoinColumn(name = "user_id") ]
    )
    @Column(name = "roles")
    var rolesSet: MutableSet<String> = mutableSetOf()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UserEntity

            if (userId != other.userId) return false

            return true
        }

        override fun hashCode(): Int {
            return userId ?: 0
        }
}
