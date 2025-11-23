import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppd_p01.domain.repository.HabitRepository
import com.example.ppd_p01.ui.screens.perfil.PerfilViewModel

class PerfilViewModelFactory(
    private val repository: HabitRepository,
    private val userId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            return PerfilViewModel(repository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
