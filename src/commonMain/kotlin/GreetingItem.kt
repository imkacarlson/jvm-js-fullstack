import kotlinx.serialization.Serializable

@Serializable
data class GreetingItem(val greeting: String) {
    companion object {
        const val path = "/shoppingList"
    }
}