package kr.co.lion.assignment_01

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kr.co.lion.assignment_01.databinding.ActivityWriteBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WriteActivity : AppCompatActivity() {

    lateinit var activityWriteBinding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityWriteBinding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(activityWriteBinding.root)

        initData()
        initToolbar()
        initView()
        setEvent()
    }

    // 초기 데이터 셋팅
    fun initData(){

    }

    // Toolbar 설정
    fun initToolbar(){
        activityWriteBinding.apply {
            toolbarWrite.apply {
                // 타이틀
                title = "메모 작성"
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                // 메뉴
                inflateMenu(R.menu.menu_write)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_write_item_check -> {
                            processWriteDone()
                        }
                    }
                    true
                }
            }
        }
    }

    fun processWriteDone() {
        activityWriteBinding.apply {
            // 사용자가 입력한 값들을 가져온다
            val title = textInputWriteTitle.text.toString()
            val content = textInputWriteContent.text.toString()
            // 날짜 가져오기
            val currentDate : Long = System.currentTimeMillis()
            val formatDate = android.icu.text.SimpleDateFormat("yyyy-MM-dd-E")
            val date = (formatDate.format(currentDate)).toString()

            // 입력받은 정보를 객체에 담아준다
            val memoData = MemoData(title, content, date)
            // 이전으로 돌아간다
            val resultIntent = Intent()
            resultIntent.putExtra("memoData", memoData)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    // View 초기 셋팅
    fun initView(){

    }

    // 이벤트 설정
    fun setEvent(){

    }
}