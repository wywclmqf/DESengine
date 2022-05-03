import os
import plotly.graph_objects as go
import plotly.io as pio
import plotly.express as px

pio.kaleido.scope.mathjax = None


fig = go.Figure()

fig.add_trace(go.Bar(name="Central", x=[" "], y=[1563426.905], legendrank=1, width=[0.18]
                     , marker_line_color='rgb(99,110,250)', marker_pattern_shape="."))
fig.add_trace(go.Bar(name="Disco", x=[" "], y=[40379], legendrank=2, width=[0.18]
                     , marker_line_color='rgb(239,85,59)', marker_pattern_shape="/"))
fig.add_trace(go.Bar(name="Scotty", x=[" "], y=[7353263], legendrank=3, width=[0.18]
                     , marker_line_color='rgb(0,204,150)', marker_pattern_shape="+"))
fig.add_trace(go.Bar(name="Desis", x=[" "], y=[7361659.16], legendrank=4, width=[0.18]
                     , marker_line_color='rgb(171,99,250)', marker_pattern_shape="\\"))
# fig.add_trace(go.Bar(name="DesisSw", x=[" "], y=[30545075.4], legendrank=4, width=[0.18]
#                      , marker_line_color='rgb(255,161,90)', marker_pattern_shape="-"))
# fig.update_traces(marker_color='rgb(158,202,225)', marker_line_color='rgb(8,48,107)', marker_line_width=1.5, opacity=0.6)


#legend
fig.update_layout(
    legend=dict(
        yanchor="top",
        y=0.99,
        xanchor="left",
        x=0.01,
        # bordercolor="Black",
        # borderwidth=2,
        # bgcolor="white",
        font=dict(
            size=10,
            color="black"
        ),
    ),
    yaxis=dict(
        title_text="events/sec",
        titlefont=dict(size=15),
        exponentformat="e",
        ticktext=["0", "0.5M", "1M", "2M", "4M", "7M"],
        tickvals=[0, 500000, 1000000, 2000000, 4000000, 7000000],
        range=[0, 8000000],
        tickmode="array",
    )
)

# size & color
fig.update_layout(
    autosize=False,
    width=350,
    height=500,
    paper_bgcolor='rgba(0,0,0,0)',
    plot_bgcolor='rgba(0,0,0,0)'
)
# fig = px.bar(x=["a","b","c"], y=[1,3,2], color=["red", "goldenrod", "#00D"], color_discrete_map="identity")
fig.update_layout(barmode='group', bargap=0.2, bargroupgap=0.0)

# fig.update_yaxes(automargin=True)
fig.update_yaxes(ticks="outside", tickwidth=1, tickcolor='black', ticklen=5)
fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror=True)
fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror=True)


fig.show()
if not os.path.exists("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1"):
    os.mkdir("E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1")
# fig.write_image("images/fig1.svg")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1\/throughput\ThroughputM.pdf")
pio.write_image(fig, "E:\my paper\DesisPaper\Desis-Optimizing-Decentralized-Window-Aggregation\experiment\s1\/throughput\ThroughputM.svg")