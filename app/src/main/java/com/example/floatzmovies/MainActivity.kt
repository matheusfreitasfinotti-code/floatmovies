package com.example.floatzmovies

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.UUID

val RoxoFosco = Color(0xFF391847)
val RoxoFoscoClaro = Color(0xFF5A2A6E)
val FundoPrincipal = Color(0xFF121212)
val CardFundo = Color(0xFF1E1E1E)

data class Filme(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val genero: String,
    val ano: Int,
    val sinopse: String,
    val direcao: String,
    @DrawableRes val imagemRes: Int? = null,
    val criadoPorMim: Boolean = false
)

class FilmeViewModel : ViewModel() {
    private val _filmes = MutableStateFlow<List<Filme>>(
        listOf(
            Filme(
                titulo = "Os Suspeitos",
                genero = "Suspense / Policial",
                ano = 2013,
                sinopse = "Keller Dover enfrenta o pior pesadelo de um pai quando sua filha de seis anos e a amiga dela desaparecem. A única pista é uma van decadente que estava estacionada na rua. O detetive Loki prende o motorista, mas a falta de provas obriga a polícia a libertá-lo. Desesperado, Keller decide caçar o responsável por conta própria.",
                direcao = "Denis Villeneuve",
                imagemRes = R.drawable.os_suspeitos,
                criadoPorMim = false
            ),
            Filme(
                titulo = "A Outra História Americana",
                genero = "Drama / Policial",
                ano = 1998,
                sinopse = "Derek Vinyard é um jovem skinhead neo-nazista que foi preso por matar dois homens negros que tentavam roubar seu carro. Anos depois, após cumprir pena, ele sai da prisão transformado e tenta desesperadamente impedir que seu irmão mais novo, Danny, siga o mesmo caminho de ódio e violência.",
                direcao = "Tony Kaye",
                imagemRes = R.drawable.outra_historia_americana,
                criadoPorMim = false
            ),
            Filme(
                titulo = "Star Wars: Episódio III – A Vingança dos Sith",
                genero = "Ficção Científica / Aventura",
                ano = 2005,
                sinopse = "As Guerras Clônicas continuam. O Chanceler Palpatine foi sequestrado, e Anakin Skywalker lidera a missão de resgate. Dividido entre sua lealdade ao mestre Obi-Wan Kenobi e as promessas sedutoras de Palpatine sobre os poderes do lado sombrio, Anakin toma uma decisão que mudará o destino da galáxia para sempre.",
                direcao = "George Lucas",
                imagemRes = R.drawable.star_wars,
                criadoPorMim = false
            ),
            Filme(
                titulo = "Até o Último Homem",
                genero = "Drama / Guerra",
                ano = 2016,
                sinopse = "A extraordinária história real de Desmond T. Doss, um médico do exército americano que, durante a Segunda Guerra Mundial na Batalha de Okinawa, recusou-se a pegar em armas ou matar pessoas. Mesmo assim, ele salvou heroicamente dezenas de vidas sob fogo cruzado, tornando-se o primeiro Ocessor de Consciência a receber a Medalha de Honra.",
                direcao = "Mel Gibson",
                imagemRes = R.drawable.ultimo_homem,
                criadoPorMim = false
            ),
            Filme(
                titulo = "The Batman",
                genero = "Ação / Policial",
                ano = 2022,
                sinopse = "Nos seus primeiros anos como vigilante, Batman investiga a elite corrupta de Gotham City quando um serial killer sádico, conhecido como O Charada, começa a assassinar figuras políticas importantes. Forçado a criar alianças inesperadas, Bruce Wayne precisa desvendar pistas e confrontar a verdade sobre o envolvimento de sua própria família.",
                direcao = "Matt Reeves",
                imagemRes = R.drawable.the_batman,
                criadoPorMim = false
            ),
            Filme(
                titulo = "Cidade de Deus",
                genero = "Drama / Crime",
                ano = 2002,
                sinopse = "No Rio de Janeiro, Buscapé é um jovem pobre, negro e sensível que cresce em um universo de muita violência na Cidade de Deus. Ele sonha em se tornar fotógrafo profissional e registra a dura realidade do tráfico de drogas na favela.",
                direcao = "Fernando Meirelles e Kátia Lund",
                imagemRes = R.drawable.cidade_deus,
                criadoPorMim = false
            ),
            Filme(
                titulo = "Tropa de Elite",
                genero = "Ação / Policial / Drama",
                ano = 2007,
                sinopse = "Capitão Nascimento do BOPE precisa encontrar um substituto para liderar sua equipe enquanto tenta equilibrar o caos da criminalidade no Rio de Janeiro e a gravidez de sua esposa, tudo isso em meio à preparação para a visita do Papa.",
                direcao = "José Padilha",
                imagemRes = R.drawable.tropa_elite,
                criadoPorMim = false
            ),
            Filme(
                titulo = "Estômago",
                genero = "Comédia dramática / Crime",
                ano = 2007,
                sinopse = "Nonato migra para São Paulo em busca de uma vida melhor. Ele consegue emprego em um bar e depois em um restaurante italiano refinado, descobrindo um talento nato para a culinária que muda o rumo da sua vida e das suas relações.",
                direcao = "Marcos Jorge",
                imagemRes = R.drawable.estomago,
                criadoPorMim = false
            )
        )
    )
    val filmes: StateFlow<List<Filme>> = _filmes.asStateFlow()

    fun adicionarFilme(titulo: String, genero: String, ano: Int, sinopse: String, direcao: String) {
        val novoFilme = Filme(
            titulo = titulo.trim(),
            genero = genero.trim(),
            ano = ano,
            sinopse = if (sinopse.isBlank()) "Sem sinopse informada." else sinopse.trim(),
            direcao = if (direcao.isBlank()) "Não informado" else direcao.trim(),
            imagemRes = android.R.drawable.ic_menu_gallery,
            criadoPorMim = true
        )
        _filmes.value = _filmes.value + novoFilme
    }

    fun atualizarFilme(id: String, novoTitulo: String, novoGenero: String, novoAno: Int, novaSinopse: String, novaDirecao: String) {
        _filmes.value = _filmes.value.map { filme ->
            if (filme.id == id) {
                filme.copy(
                    titulo = novoTitulo.trim(),
                    genero = novoGenero.trim(),
                    ano = novoAno,
                    sinopse = novaSinopse.trim(),
                    direcao = novaDirecao.trim()
                )
            } else {
                filme
            }
        }
    }

    fun excluirFilme(id: String) {
        _filmes.value = _filmes.value.filter { it.id != id }
    }

    fun buscarFilmePorId(id: String?): Filme? {
        return _filmes.value.find { it.id == id }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cadastro : Screen("cadastro")
    object Detalhes : Screen("detalhes/{filmeId}") {
        fun createRoute(filmeId: String) = "detalhes/$filmeId"
    }
    object Editar : Screen("editar/{filmeId}") {
        fun createRoute(filmeId: String) = "editar/$filmeId"
    }
    object Sobre : Screen("sobre")
}

@Composable
fun AppNavigation(viewModel: FilmeViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToCadastro = { navController.navigate(Screen.Cadastro.route) },
                onNavigateToDetalhes = { filmeId -> navController.navigate(Screen.Detalhes.createRoute(filmeId)) },
                onNavigateToSobre = { navController.navigate(Screen.Sobre.route) }
            )
        }
        composable(Screen.Cadastro.route) {
            CadastroScreen(
                onSalvarFilme = { titulo, genero, ano, sinopse, direcao ->
                    viewModel.adicionarFilme(titulo, genero, ano, sinopse, direcao)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.Detalhes.route,
            arguments = listOf(navArgument("filmeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val filmeId = backStackEntry.arguments?.getString("filmeId")
            DetalhesScreen(
                filmeId = filmeId,
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() },
                onEditar = { id -> navController.navigate(Screen.Editar.createRoute(id)) },
                onExcluirSucesso = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.Editar.route,
            arguments = listOf(navArgument("filmeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val filmeId = backStackEntry.arguments?.getString("filmeId")
            EditarScreen(
                filmeId = filmeId,
                viewModel = viewModel,
                onSalvar = { navController.popBackStack() },
                onCancelar = { navController.popBackStack() }
            )
        }
        composable(Screen.Sobre.route) {
            SobreScreen(onVoltar = { navController.popBackStack() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FilmeViewModel,
    onNavigateToCadastro: () -> Unit,
    onNavigateToDetalhes: (String) -> Unit,
    onNavigateToSobre: () -> Unit
) {
    val filmes by viewModel.filmes.collectAsState()

    Scaffold(
        containerColor = FundoPrincipal,
        topBar = {
            TopAppBar(
                title = { Text("Float Movies", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoxoFosco),
                actions = {
                    IconButton(onClick = onNavigateToSobre) {
                        Icon(Icons.Default.Info, contentDescription = "Sobre", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCadastro,
                containerColor = RoxoFoscoClaro,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Cadastrar Filme")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Catálogo de Filmes",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFB0B0B0)
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filmes) { filme ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToDetalhes(filme.id) },
                        colors = CardDefaults.cardColors(containerColor = CardFundo)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            filme.imagemRes?.let { resId ->
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = filme.titulo,
                                    modifier = Modifier
                                        .size(width = 60.dp, height = 90.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.DarkGray),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = filme.titulo, style = MaterialTheme.typography.titleMedium, color = Color.White)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Gênero: ${filme.genero}", style = MaterialTheme.typography.bodySmall, color = Color(0xFFB0B0B0))
                                Text(text = "Ano: ${filme.ano}", style = MaterialTheme.typography.bodySmall, color = Color(0xFFB0B0B0))
                                if (filme.criadoPorMim) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Criado por você", style = MaterialTheme.typography.labelSmall, color = Color(0xFFBB86FC))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    onSalvarFilme: (String, String, Int, String, String) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var genero by rememberSaveable { mutableStateOf("") }
    var anoStr by rememberSaveable { mutableStateOf("") }
    var direcao by rememberSaveable { mutableStateOf("") }
    var sinopse by rememberSaveable { mutableStateOf("") }

    var erroTitulo by rememberSaveable { mutableStateOf(false) }
    var erroGenero by rememberSaveable { mutableStateOf(false) }
    var erroAno by rememberSaveable { mutableStateOf(false) }
    var mensagemErroAno by rememberSaveable { mutableStateOf("") }
    var isSubmitting by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = FundoPrincipal,
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Novo Filme", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoxoFosco)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it; erroTitulo = false },
                label = { Text("Título do Filme *") },
                isError = erroTitulo,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            if (erroTitulo) Text("O título não pode ficar vazio.", color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = genero,
                onValueChange = { genero = it; erroGenero = false },
                label = { Text("Gênero *") },
                isError = erroGenero,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            if (erroGenero) Text("O gênero não pode ficar vazio.", color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = anoStr,
                onValueChange = { anoStr = it; erroAno = false },
                label = { Text("Ano de Lançamento *") },
                isError = erroAno,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            if (erroAno) Text(mensagemErroAno, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = direcao,
                onValueChange = { direcao = it },
                label = { Text("Direção") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )

            OutlinedTextField(
                value = sinopse,
                onValueChange = { sinopse = it },
                label = { Text("Sinopse") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(onClick = onCancelar, modifier = Modifier.weight(1f)) {
                    Text("Cancelar", color = Color.White)
                }

                Button(
                    onClick = {
                        if (isSubmitting) return@Button
                        val trimmedTitulo = titulo.trim()
                        val trimmedGenero = genero.trim()
                        val anoInt = anoStr.toIntOrNull()
                        val anoAtual = Calendar.getInstance().get(Calendar.YEAR)

                        var hasError = false
                        if (trimmedTitulo.isEmpty()) { erroTitulo = true; hasError = true }
                        if (trimmedGenero.isEmpty()) { erroGenero = true; hasError = true }
                        if (anoInt == null || anoInt <= 1888 || anoInt > anoAtual + 5) {
                            erroAno = true
                            mensagemErroAno = "Informe um ano válido."
                            hasError = true
                        }

                        if (!hasError) {
                            isSubmitting = true
                            onSalvarFilme(trimmedTitulo, trimmedGenero, anoInt!!, sinopse, direcao)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = RoxoFoscoClaro)
                ) {
                    Text("Salvar Filme", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesScreen(
    filmeId: String?,
    viewModel: FilmeViewModel,
    onVoltar: () -> Unit,
    onEditar: (String) -> Unit,
    onExcluirSucesso: () -> Unit
) {
    val filme = viewModel.buscarFilmePorId(filmeId)
    var mostrarDialogoExclusao by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = FundoPrincipal,
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Filme", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                actions = {
                    if (filme != null && filme.criadoPorMim) {
                        IconButton(onClick = { onEditar(filme.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                        }
                        IconButton(onClick = { mostrarDialogoExclusao = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color.Red)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoxoFosco)
            )
        }
    ) { innerPadding ->
        if (mostrarDialogoExclusao) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoExclusao = false },
                title = { Text("Excluir Filme") },
                text = { Text("Tem certeza que deseja excluir '${filme?.titulo}'?") },
                confirmButton = {
                    TextButton(onClick = {
                        if (filme != null) {
                            viewModel.excluirFilme(filme.id)
                            mostrarDialogoExclusao = false
                            onExcluirSucesso()
                        }
                    }) {
                        Text("Excluir", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogoExclusao = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (filme == null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Filme não encontrado.", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = onVoltar, colors = ButtonDefaults.buttonColors(containerColor = RoxoFoscoClaro)) {
                        Text("Voltar ao Catálogo", color = Color.White)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    filme.imagemRes?.let { resId ->
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = filme.titulo,
                            modifier = Modifier
                                .height(280.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.DarkGray),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CardFundo)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = filme.titulo, style = MaterialTheme.typography.headlineSmall, color = Color.White)
                            if (filme.criadoPorMim) {
                                Text(text = "Criado por você", style = MaterialTheme.typography.labelMedium, color = Color(0xFFBB86FC))
                            }
                            HorizontalDivider(color = Color(0xFF2C2C2C))
                            Text(text = "Gênero: ${filme.genero}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Text(text = "Ano: ${filme.ano}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Text(text = "Direção: ${filme.direcao}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Sinopse:", style = MaterialTheme.typography.titleMedium, color = Color(0xFFBB86FC))
                            Text(text = filme.sinopse, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarScreen(
    filmeId: String?,
    viewModel: FilmeViewModel,
    onSalvar: () -> Unit,
    onCancelar: () -> Unit
) {
    val filme = viewModel.buscarFilmePorId(filmeId)

    var titulo by rememberSaveable { mutableStateOf(filme?.titulo ?: "") }
    var genero by rememberSaveable { mutableStateOf(filme?.genero ?: "") }
    var anoStr by rememberSaveable { mutableStateOf(filme?.ano?.toString() ?: "") }
    var direcao by rememberSaveable { mutableStateOf(filme?.direcao ?: "") }
    var sinopse by rememberSaveable { mutableStateOf(filme?.sinopse ?: "") }

    var erroTitulo by rememberSaveable { mutableStateOf(false) }
    var erroGenero by rememberSaveable { mutableStateOf(false) }
    var erroAno by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = FundoPrincipal,
        topBar = {
            TopAppBar(
                title = { Text("Editar Filme", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoxoFosco)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it; erroTitulo = false },
                label = { Text("Título do Filme *") },
                isError = erroTitulo,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            if (erroTitulo) Text("O título não pode ficar vazio.", color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = genero,
                onValueChange = { genero = it; erroGenero = false },
                label = { Text("Gênero *") },
                isError = erroGenero,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )
            if (erroGenero) Text("O gênero não pode ficar vazio.", color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = anoStr,
                onValueChange = { anoStr = it; erroAno = false },
                label = { Text("Ano de Lançamento *") },
                isError = erroAno,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )

            OutlinedTextField(
                value = direcao,
                onValueChange = { direcao = it },
                label = { Text("Direção") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )

            OutlinedTextField(
                value = sinopse,
                onValueChange = { sinopse = it },
                label = { Text("Sinopse") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(onClick = onCancelar, modifier = Modifier.weight(1f)) {
                    Text("Cancelar", color = Color.White)
                }

                Button(
                    onClick = {
                        val trimmedTitulo = titulo.trim()
                        val trimmedGenero = genero.trim()
                        val anoInt = anoStr.toIntOrNull() ?: 2023

                        if (trimmedTitulo.isNotEmpty() && trimmedGenero.isNotEmpty() && filmeId != null) {
                            viewModel.atualizarFilme(filmeId, trimmedTitulo, trimmedGenero, anoInt, sinopse, direcao)
                            onSalvar()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = RoxoFoscoClaro)
                ) {
                    Text("Salvar Alterações", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SobreScreen(onVoltar: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        containerColor = FundoPrincipal,
        topBar = {
            TopAppBar(
                title = { Text("Sobre o Aplicativo", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoxoFosco)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Float Movies", style = MaterialTheme.typography.headlineMedium, color = RoxoFoscoClaro)
            Text(text = "Finalidade: Organizar e consultar informações detalhadas de filmes de forma simples e intuitiva.", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            Text(
                text = "Instruções de uso:\n• Navegue pelo catálogo inicial para ver os filmes.\n• Cadastre seus próprios filmes pelo botão '+'.\n• Filmes criados por você possuem opções de edição e exclusão na tela de detalhes.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB0B0B0)
            )
            HorizontalDivider(color = Color(0xFF2C2C2C))
            Text(text = "Autor: Matheus Finotti e Murillo Rangel", style = MaterialTheme.typography.bodyMedium, color = RoxoFoscoClaro)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val textoCompartilhar = "Conheça o Float Movies! Um aplicativo para catálogo de filmes desenvolvido por Matheus Finotti e Murillo Rangel."
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, textoCompartilhar)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Compartilhar Float Movies via")
                    try { context.startActivity(shareIntent) } catch (e: Exception) {}
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RoxoFoscoClaro)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compartilhar aplicativo", color = Color.White)
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}