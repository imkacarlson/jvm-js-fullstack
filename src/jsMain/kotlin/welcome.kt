import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import styled.css
import styled.styledDiv
import styled.styledInput
import react.dom.*
import styled.*

import Polyglot
import kotlinx.html.js.onClickFunction
import react.*

var polyglotEN = Polyglot()
var polyglotES = Polyglot()

external interface WelcomeProps : RProps {
    var phrase: String
    var cars: String
    var english : Boolean
}

class WelcomeState(val phrase: String, val cars: String, val english: Boolean) : RState

@JsExport
class Welcome(props: WelcomeProps) : RComponent<WelcomeProps, WelcomeState>(props) {

    init {
        state = WelcomeState(props.phrase, props.phrase, true)

        polyglotEN.extend(phrases = js("{'main': {'greeting': 'Hello World!' }, 'car_pluralization': '%{smart_count} car |||| %{smart_count} cars'}"))

        polyglotES.extend(phrases = js("{'main': {'greeting': 'Holla Mundo!' }, 'car_pluralization': '%{smart_count} coche |||| %{smart_count} coches'}"))
    }

    override fun RBuilder.render() {
        div {
            p {
                + state.phrase
            }
        }

        button {
            attrs.onClickFunction = { event ->
                setState(
                    WelcomeState(phrase = polyglotES.t("main.greeting"),
                        cars = polyglotES.t("car_pluralization", 2), false)
                )
            }
            + "Spanish"
        }

        button {
            attrs.onClickFunction = { event ->
                setState(
                    WelcomeState(phrase = polyglotEN.t("main.greeting"),
                        cars = polyglotEN.t("car_pluralization", 2), true)
                )
            }
            + "English"
        }
        div {
            styledInput {
                css {
                    +WelcomeStyles.textInput
                }
                attrs {
                    type = InputType.text
                    onChangeFunction = { event ->
                        setState(
                            if(state.english) {
                                WelcomeState(phrase = polyglotEN.t("main.greeting"),
                                    cars = polyglotEN.t("car_pluralization", ((event.target as HTMLInputElement).value).toInt()), true)
                            }else{
                                WelcomeState(phrase = polyglotES.t("main.greeting"),
                                    cars = polyglotES.t("car_pluralization", ((event.target as HTMLInputElement).value).toInt()), false)
                            })
                    }
                }
            }
        }
        div {
            p {
                + state.cars
            }
        }
//        div {
//            p {
//                + getPhrases()
//            }
//        }
    }
}
