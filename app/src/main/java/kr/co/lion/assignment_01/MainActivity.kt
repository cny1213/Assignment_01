package kr.co.lion.assignment_01

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.assignment_01.databinding.ActivityMainBinding
import kr.co.lion.assignment_01.databinding.RowMainBinding

// 테킷 2차과제 - 메모 앱 만들기

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding:ActivityMainBinding

    // WriteActivity의 런처
    lateinit var writeActivityLauncher : ActivityResultLauncher<Intent>

    // ShowActivity의 런처
    lateinit var showActivityLauncher : ActivityResultLauncher<Intent>

    // 메모 정보를 담을 리스트
    val memoList = mutableListOf<MemoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initData()
        setToolbar()
        setView()
    }

    // 기본 데이터 및 객체 생성
    fun initData(){
        // WriteActivity의 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        writeActivityLauncher = registerForActivityResult(contract1){
            // 작업 결과가 OK 라면
            if(it.resultCode == RESULT_OK){
                // 전달된 Intent객체가 있다면
                if(it.data != null){
                    // 메모 객체를 추출한다
                    // 버전을 나눠야해용....
                    if(Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
                        val memoData = it.data?.getParcelableExtra("memoData", MemoData::class.java)
                        memoList.add(memoData!!)
                        activityMainBinding.recyclerViewItem.adapter?.notifyDataSetChanged()
                    } else{
                        val memoData = it.data?.getParcelableExtra<MemoData>("memoData")
                        memoList.add(memoData!!)
                        activityMainBinding.recyclerViewItem.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        // ShowActivity의 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        showActivityLauncher = registerForActivityResult(contract2){

        }
    }

    // 툴바 설정
    fun setToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                title = "메모 관리"
                inflateMenu(R.menu.menu_main)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_main_item_input-> {
                            // WriteActivity를 실행한다
                            val writeIntent = Intent(this@MainActivity, WriteActivity::class.java)
                            writeActivityLauncher.launch(writeIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun setView(){
        activityMainBinding.apply {
            recyclerViewItem.apply {
                // 어뎁터 설정
                adapter = RecyclerViewMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 구분선
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        // ViewHolder
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding : RowMainBinding

            init{
                this.rowMainBinding = rowMainBinding
                // 항목 클릭시 전체가 클릭이 될 수 있도록
                // 가로 세로 길이를 설정해준다.
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 항목을 눌렀을 때의 리스너
                this.rowMainBinding.root.setOnClickListener {
                    // ShowActivity를 실행한다.
                    val showIntent = Intent(this@MainActivity, ShowActivity::class.java)
                    // 선택한 항목의 객체를 intent에 담아준다.
                    showIntent.putExtra("memoData",memoList[adapterPosition])

                    // 현재 번째의 순서값을 담아준다.
                    showIntent.putExtra("position", adapterPosition)

                    showActivityLauncher.launch(showIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(rowMainBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowMainBinding.textViewRowMainTitle.text = "제목: ${memoList[position].title}"
            holder.rowMainBinding.textViewRowMainDate.text = "날짜: ${memoList[position].date}"
        }
    }
}