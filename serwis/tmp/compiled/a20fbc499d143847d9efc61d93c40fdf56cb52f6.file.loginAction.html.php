<?php /* Smarty version Smarty-3.0-RC2, created on 2015-10-30 22:31:37
         compiled from "appl_backend/view/users/loginAction.html" */ ?>
<?php /*%%SmartyHeaderCode:14315633e1b94f0917-34981794%%*/if(!defined('SMARTY_DIR')) exit('no direct access allowed');
$_smarty_tpl->decodeProperties(array (
  'file_dependency' => 
  array (
    'a20fbc499d143847d9efc61d93c40fdf56cb52f6' => 
    array (
      0 => 'appl_backend/view/users/loginAction.html',
      1 => 1446239885,
    ),
  ),
  'nocache_hash' => '14315633e1b94f0917-34981794',
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