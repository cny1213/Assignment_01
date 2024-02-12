package kr.co.lion.assignment_01

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.assignment_01.Util.Companion.memoList
import kr.co.lion.assignment_01.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {

    lateinit var activityShowBinding: ActivityShowBinding

    // ModifyActivity 런처
    lateinit var modifyActivityLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(activityShowBinding.root)

        initData()
        initToolbar()
        initView()
    }

    // 초기 데이터 셋팅
    fun initData(){

        // ModifyActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        modifyActivityLauncher = registerForActivityResult(contract1){

        }
    }

    // Toolbar 설정
    fun initToolbar(){
        activityShowBinding.apply {
            toolbarShow.apply {
                // 타이틀
                title = "메모 보기"
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴 등록
                inflateMenu(R.menu.menu_show)
                // 메뉴 클릭시 이벤트 설정
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 수정
                        R.id.menu_show_item_modify -> {
                            // ModifyActivity를 실행한다.
                            val modifyIntent = Intent(this@ShowActivity,ModifyActivity::class.java)
                            // 메모 순서값을 지정한다
                            val position = intent.getIntExtra("position",0)
                            modifyIntent.putExtra("position",position)

                            modifyActivityLauncher.launch(modifyIntent)
                        }
                        // 삭제
                        R.id.menu_show_item_delete -> {
                            // 항목 순서 값을 가져온다
                            val position = intent.getIntExtra("position",0)
                            // 항목 번째 객체를 리스트에서 제거한다
                            memoList.removeAt(position)
                            finish()

                        }
                    }
                    true
                }
            }
        }
    }

    // View 초기 셋팅
    fun initView(){
        activityShowBinding.apply {
            textViewShowResult.apply {

                // 받아온 Intent로 부터 메모정보를 추출한다.
                val memoData = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    intent.getParcelableExtra("memoData", MemoData::class.java)
                } else {
                    intent.getParcelableExtra<MemoData>("memoData")
                }

                text = "제목 : ${memoData?.title}\n"
                append("내용 : ${memoData?.content} \n\n")
                append("날짜 : ${memoData?.date}")

            }
        }

    }

    // 이벤트 설정
    fun setEvent(){

    }
}