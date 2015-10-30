<?php /* Smarty version Smarty-3.0-RC2, created on 2015-10-30 22:34:49
         compiled from "appl_serwis/view/users/loginAction.html" */ ?>
<?php /*%%SmartyHeaderCode:16035633e279798388-06374833%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    '4b5c54457c09304d5faf95a16562eb6e18c417a0' => 
    array (
      0 => 'appl_serwis/view/users/loginAction.html',
      1 => 1446239885,
    ),
  ),
  'nocache_hash' => '16035633e279798388-06374833',
  'function' => 
  array (
  ),
  'has_nocache_code' => false,
)); /*/%%SmartyHeaderCode%%*/?>
<p style="color:red;"><?php echo $_smarty_tpl->getVariable('message')->value;?>
</p>
<?php if ($_SESSION['logged']!=true){?>
	<form method="POST" action="">
		<input type="text" name="login"><br>
		<input type="text" name="pass"><br>
		<input type="submit" value="Zaloguj"><br>
	</form>
<?php }else{ ?>
	Witamy zalogowany userze.
<?php }?>