#target node ip list
server_ip_list=(
192.168.0.2
192.168.0.3
192.168.0.4
)

#ssh port
ssh_port=`head -n 10 /etc/ssh/sshd_config | grep Port | awk '{print $2}'`

# ssh user id
ssh_user=fastcat

# fastcatsearch install home path
this_home=/opt/fastcatsearch
target_home=$this_home
