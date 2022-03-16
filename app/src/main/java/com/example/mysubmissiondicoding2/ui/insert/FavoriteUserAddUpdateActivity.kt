package com.example.mysubmissiondicoding2.ui.insert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiondicoding2.R
import com.example.mysubmissiondicoding2.data.local.FavoriteUser
import com.example.mysubmissiondicoding2.databinding.ActivityFavoriteUserAddUpdateBinding
import com.example.mysubmissiondicoding2.ui.detail.DetailUserActivity
import com.example.mysubmissiondicoding2.helper.ViewModelFactory

class FavoriteUserAddUpdateActivity : AppCompatActivity() {
    private lateinit var favoriteUserUpdateViewModel: FavoriteUserAddUpdateViewModel
    private var _binding: ActivityFavoriteUserAddUpdateBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteUserAdapter

    companion object {
        const val EXTRA_USERNAME = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private val favoriteUser:FavoriteUser? = null
    private var isDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFavoriteUserAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = FavoriteUserAdapter()
        adapter.notifyDataSetChanged()
        favoriteUserUpdateViewModel = obtainViewModel(this)

        adapter.setOnClickCallback(object :FavoriteUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FavoriteUser) {
                Intent(this@FavoriteUserAddUpdateActivity, DetailUserActivity::class.java).let {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL,data.avatarUrl)
                    startActivity(it)
                }
            }
        })

//        if (favoriteUsername != null){
//            isDelete = true
//        }

        binding?.apply {
            rvFavoriteUser.setHasFixedSize(true)
            rvFavoriteUser.layoutManager = LinearLayoutManager(this@FavoriteUserAddUpdateActivity)
            rvFavoriteUser.adapter = adapter
        }

        favoriteUserUpdateViewModel.getAllFavUser().observe(this,{
            if (it != null){
                adapter.setListFavUsers(it)
            }else{
                isDelete = false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_file, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {

                        favoriteUserUpdateViewModel.delete(favoriteUser as FavoriteUser)
                        showToast(getString(R.string.deleted))
//                        showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserAddUpdateViewModel::class.java)

    }
}