package compsevice.ua.app.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import compsevice.ua.app.R
import compsevice.ua.app.activity.adapter.ElectricityConsumingAdapter
import compsevice.ua.app.activity.model.ConsumingBySectors
import kotlinx.android.synthetic.main.activity_electricity_consuming.*

val ITEMS = listOf(
        ConsumingBySectors(1, 120_121.20),
        ConsumingBySectors(2, 110_127.35),
        ConsumingBySectors(3, 90_126.12),
        ConsumingBySectors(4, 134_129.55),
        ConsumingBySectors(5, 75_423.84),
        ConsumingBySectors(6, 63_143.21),
        ConsumingBySectors(7, 158_323.37),
        ConsumingBySectors(8, 100_823.65)


)

class ElectricityConsumingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_consuming)

        consumingBySectors.adapter = ElectricityConsumingAdapter(ITEMS)
        consumingBySectors.setLayoutManager(LinearLayoutManager(this))
    }
}
