package com.aigardev.tictactoe.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily.Companion.Cursive
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aigardev.tictactoe.BoardCellValue
import com.aigardev.tictactoe.GameState
import com.aigardev.tictactoe.GameViewModel
import com.aigardev.tictactoe.R
import com.aigardev.tictactoe.UserAction
import com.aigardev.tictactoe.VictoryType
import com.aigardev.tictactoe.ui.components.BoardBase
import com.aigardev.tictactoe.ui.components.Circle
import com.aigardev.tictactoe.ui.components.Cross
import com.aigardev.tictactoe.ui.components.SwitchPlayer2
import com.aigardev.tictactoe.ui.components.WinDiagonalLine1
import com.aigardev.tictactoe.ui.components.WinDiagonalLine2
import com.aigardev.tictactoe.ui.components.WinHorizontalLine1
import com.aigardev.tictactoe.ui.components.WinHorizontalLine2
import com.aigardev.tictactoe.ui.components.WinHorizontalLine3
import com.aigardev.tictactoe.ui.components.WinVerticalLine1
import com.aigardev.tictactoe.ui.components.WinVerticalLine2
import com.aigardev.tictactoe.ui.components.WinVerticalLine3
import com.aigardev.tictactoe.ui.theme.Aqua
import com.aigardev.tictactoe.ui.theme.BlueCustom
import com.aigardev.tictactoe.ui.theme.DarkGray
import com.aigardev.tictactoe.ui.theme.GrayBackground
import com.aigardev.tictactoe.ui.theme.GreenishYellow

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = Bold,
            fontFamily = Cursive,
            color = BlueCustom
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //val fontWeight = FontWeight.Bold
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //Text(text = "Player 'X'", fontSize = 16.sp)
                Image(painter = convertDrawableToPainter(R.drawable.round_close_24), contentDescription = null, modifier = Modifier.requiredHeight(24.dp))
                Text(text = "${state.playerCrossCount} wins", fontSize = 16.sp, fontWeight = Bold, color = GreenishYellow)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //Text(text = "Draw", fontSize = 16.sp)
                Image(painter = convertDrawableToPainter(R.drawable.round_balance_24), contentDescription = null, modifier = Modifier.requiredHeight(24.dp))
                Text(text = "${state.drawCount} draws", fontSize = 16.sp, fontWeight = Bold, color = DarkGray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //Text(text = "Player '0'", fontSize = 16.sp)
                Image(painter = convertDrawableToPainter(R.drawable.outline_circle_24), contentDescription = null, modifier = Modifier.requiredHeight(24.dp))
                Text(text = "${state.playerCircleCount} wins", fontSize = 16.sp, fontWeight = Bold, color = Aqua)
            }
            //Text(text = "Player '0': 0", fontSize = 16.sp)
            //Text(text = "Draw: 0", fontSize = 16.sp)
            //Text(text = "Player 'X': 0", fontSize = 16.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ){
            BoardBase()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3)
            ) {
                viewModel.boardItems.forEach { (cellNum, boardCellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    viewModel.onAction(
                                        UserAction.BoardTapped(cellNum)
                                    )
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = viewModel.boardItems[cellNum] != BoardCellValue.NONE,
                                enter = scaleIn(tween(500))
                            ) {
                                if (boardCellValue == BoardCellValue.CIRCLE) {
                                    Circle()
                                } else if (boardCellValue == BoardCellValue.CROSS) {
                                    Cross()
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.hasWon,
                    enter = fadeIn(tween(2000))
                ) {
                    DrawVictoryLine(state = state)
                }
            }
        }

        /*
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {

        }
        */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {
            SwitchPlayer2()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            //contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        viewModel.onAction(
                            UserAction.ResetGameButtonClicked
                        )
                    },
                    modifier = Modifier
                        .border(BorderStroke(0.dp, color = GrayBackground))
                        .background(color = GrayBackground)
                ) {
                    Image(painter = convertDrawableToPainter(R.drawable.round_refresh_24), contentDescription = null, modifier = Modifier.requiredHeight(24.dp))
                }

                Button(
                    onClick = {
                        viewModel.onAction(
                            UserAction.PlayAgainButtonClicked
                        )
                    },
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.elevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlueCustom,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Play Again", fontSize = 14.sp)
                }

                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.elevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GrayBackground,
                        contentColor = Color.White
                    )
                ) {
                    Image(painter = convertDrawableToPainter(R.drawable.round_settings_24), contentDescription = null, modifier = Modifier.requiredHeight(24.dp))
                }
            }

        }

        /*
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            /*
            Text(
                text = state.hintText,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic
            )*/
            Button(
                onClick = {
                          viewModel.onAction(
                              UserAction.PlayAgainButtonClicked
                          )
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueCustom,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Play Again", fontSize = 14.sp)
            }
        }*/
    }
}

@Composable
fun DrawVictoryLine(
    state: GameState
) {
    when(state.victoryType) {
        VictoryType.HORIZONTAL1 -> WinHorizontalLine1()
        VictoryType.HORIZONTAL2 -> WinHorizontalLine2()
        VictoryType.HORIZONTAL3 -> WinHorizontalLine3()
        VictoryType.VERTICAL1 -> WinVerticalLine1()
        VictoryType.VERTICAL2 -> WinVerticalLine2()
        VictoryType.VERTICAL3 -> WinVerticalLine3()
        VictoryType.DIAGONAL1 -> WinDiagonalLine1()
        VictoryType.DIAGONAL2 -> WinDiagonalLine2()
        VictoryType.NONE -> {}
    }
}

@Composable
fun convertDrawableToPainter(drawableId: Int): Painter {
    return painterResource(drawableId)
}

@Preview
@Composable
fun Prev() {
    GameScreen(
        viewModel = GameViewModel()
    )
}