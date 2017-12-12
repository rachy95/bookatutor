# Install the server library with:
#   gem install pusher -v '>= 1.2.0'

require 'pusher'

pusher_client = Pusher::Client.new(
  app_id:    '424832',
  key:       '575e480f6f4c0941387e',
  secret:    'd42c6b7d26dded3c235c',
  cluster:   'us2',
  encrypted: true
)

data = {
  gcm: {
    notification: {
      title: "Hello World!",
      icon: "icon"
    }
  }
}

puts pusher.notify(["kittens"], data)
