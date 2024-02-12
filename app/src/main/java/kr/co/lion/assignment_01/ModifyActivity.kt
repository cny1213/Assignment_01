package kr.co.lion.assignment_01

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.assignment_01.databinding.ActivityModifyBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var activityModifyBinding: ActivityModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityModifyBinding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(activityModifyBinding.root)

        initToolbar()
        initView()
    }

    // 초기 데이터 셋팅
    fun initData(){

    }

    // Toolbar 설정
    fun initToolbar(){
        activityModifyBinding.apply {
         toolbarModify.apply {
             // 타이틀
             title = "메모 수정"
             // 뒤로가기
             setNavigationIcon(R.drawable.arrow_back_24px)
             setNavigationOnClickListener {
                 setResult(RESULT_CANCELED)
                 finish()
             }
             // 메뉴 등록
             inflateMenu(R.menu.menu_modify)
         }
        }
    }

    // View 초기 셋팅
    fun initView(){
        activityModifyBinding.apply {
            // 순서값을 추출한다.
            val position = intent.getIntExtra("position", 0)
            // position 번째 객체를 추출한다.
            val memo = Util.memoList[position]

            textInputModifyTitle.setText(memo.title)
            textInputModifyContent.setText(memo.content)
        }
    }

    // 이벤트 설정
    fun setEvent(){

    }
}