package co.softov.zero.android.domain.redux

sealed class Action {
    abstract class FeatureAction : Action()
}

object NoOp : Action.FeatureAction()
object Init : Action.FeatureAction()

object UiTestAction : Action.FeatureAction()
data class TestAction(val message: String) : Action.FeatureAction()